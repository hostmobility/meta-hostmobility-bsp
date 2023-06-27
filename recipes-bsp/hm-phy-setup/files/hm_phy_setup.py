#!/usr/bin/env python3

import argparse
import struct
import subprocess
import sys
import time

class MDIOBus:
	def __init__(self, bus_name):
		self.bus_name = bus_name
		if not "mdio_netlink" in subprocess.check_output("lsmod").decode():
			subprocess.check_call(("modprobe", "mdio-netlink"))
	def	read_mmd(self, phy_addr, device, register):
		return int(subprocess.check_output(["mdio",self.bus_name,"mmd",f"{phy_addr}:{device}","raw",f"{register}"]), 16)
	def write_mmd(self, phy_addr, device, register, new_value, mask=0xFFFF):
		# Parameters raw REG [DATA[/MASK]]
		data = hex(new_value)
		if mask != 0xFFFF:
			data += f"/{hex(mask)}"
		mmd_args = ["mdio",self.bus_name,"mmd",f"{phy_addr}:{device}","raw",f"{register}", data]
		# print(mmd_args)
		subprocess.check_call(mmd_args)
	def find_phys(self):
		phys = []
		for addr in range(8):
			phy = Phy(self, addr)
			if phy.model == 0x18:
				phys.append(phy)
		return phys

class MMDRegister:
	pass


class MMDRegister:
	@classmethod
	def calculate_mask(cls, startbit, length=1):
		return ((1 << startbit + length) - 1) & ~((1 << startbit) - 1)

	@classmethod
	def get_unsigned_data (cls, data : int, startbit : int, length : int = 1) -> int:
		return (data & cls.calculate_mask(startbit, length)) >> startbit

	@classmethod
	def set_unsigned_data (cls, data : int, startbit : int, length : int = 1, value : int = 1) -> int:
		mask = cls.calculate_mask(startbit, length) 
		return (data & ~mask) | ((value << startbit) & mask)

	def __init__(self, phy, name, device_nr, reg_nr) -> None:
		self.phy = phy 
		self.name = name
		self.device_nr = device_nr
		self.reg_nr = reg_nr
		self.phy.add_register(self)
		self.data = None
	
	def fetch(self):
		self.data = self.phy.read(self.device_nr, self.reg_nr)
	
	def write(self):
		self.phy.write(self.device_nr, self.reg_nr, self.data)

	def __str__(self):
		return f"{self.name}({hex(self.device_nr)}:{hex(self.reg_nr)}) = {hex(self.data) if self.data != None else 'no data'}"

	def get_unsigned(self, start_bit : int, length : int = 1, value : int = 1) -> int:
		self.fetch()
		return self.get_unsigned_data(self.data, start_bit, length)

	def set_unsigned(self, start_bit : int, length : int = 1, value : int = 1) -> int:
		self.fetch()
		self.data = self.set_unsigned_data(self.data, start_bit, length, value)
		self.write()

class SignalInfo:
	def __init__(self, register : MMDRegister, startbit: int, length : int = 1, description : str = ""):
		self.register = register
		self.startbit = startbit
		self.length = length
		self.description = f"{description} (bit {startbit}..{startbit + length - 1})"

	def __str__(self):
		return self.description

	def __desc__(self):
		return __str__()
		
	@property
	def value(self) -> int:
		return self.register.get_unsigned(self.startbit, self.length) 

	@value.setter
	def value(self, new_value):
		self.register.set_unsigned(self.startbit, self.length, new_value)



class Phy:
	MRVL_88Q2112_AN_ENABLE = 0x1000
	def __init__(self, mdio_bus, mdio_addr):
		self.mdio_bus = mdio_bus
		self.mdio_addr = mdio_addr
		self.registers = []
		self.pma_pmd_control_reg_1  = MMDRegister(self, "PMA/PMD Control Register 1",1, 0x0000)
		self.pma_pmd_control_reg_1.low_power = SignalInfo(self.pma_pmd_control_reg_1, startbit=11,description="0=normal,1=low power")

		
		self.pcs_1000base_t1_status_reg_1 = MMDRegister(self, "PCS 1000BASE-T1 Status Register 1",3,0x0901 )
		self.auto_neg_status_reg = MMDRegister(self, "auto-neg status reg", 7, 0x8001)
		self.local_rcvr_status_2_reg = MMDRegister(self, "local rcvr status reg ", 3, 0xfd9d)

		self.lpsd_register_1 = MMDRegister(self, "LPSD Register 1", 3, 0x801c)
		self.lpsd_register_1.lpsd_power_down_enable = SignalInfo(self.lpsd_register_1, startbit=0,description="0=Disable LPSD power down,1=Enable LPSD power down")
		self.lpsd_register_1.lpsd_remote_wake_status = SignalInfo(self.lpsd_register_1, startbit=1,description="0=device did NOT wake on energy on MDIP/N pins, 1=device POWERED UP up because of energy on the MDIP/N")
		self.lpsd_register_1.lpsd_local_wake_status = SignalInfo(self.lpsd_register_1, startbit=2,description="0=device did NOT wake up from WAKE_IN, 1=device WOKE up from WAKE_IN")

		self.lpsd_register_2 = MMDRegister(self, "LPSD Register 2", 3, 0x801d)

		self.reg_100base_t1_status_1 = MMDRegister(self, "100BASE-T1 Status Register 1", 3, 0x8108)
		self.reg_100base_t1_status_2 = MMDRegister(self, "100BASE-T1 Status Register 2", 3, 0x8109)
		self.reg_receiver_status = MMDRegister(self, "Receiver Status Register", 3, 0x8230)
		self.packet_checker_control_register = MMDRegister(self, "Packet Checker Control Register", 3, 0xfd07)
		self.packet_checker_count_register = MMDRegister(self, "Packet Checker Count Register", 3, 0xfd08)
		self.packet_checker_count_register = MMDRegister(self, "Packet Checker Count Register", 3, 0xfd08)
		self.base_t1_pma_pmd_control_reg= MMDRegister(self, "BASE-T1 PMA/PMD Control Register", 1, 0x834)
		self.pcs_control_reg = MMDRegister(self, "PCS Control Register", 3, 0xfd00)
		self.pcs_control_reg.mux_a = SignalInfo(self.pcs_control_reg, startbit=7, length=2, description="0|1=line RX to host, 2=host RX to host, 3=PG to host")
		self.pcs_control_reg.mux_b = SignalInfo(self.pcs_control_reg, startbit=9, length=2, description="0|1=host TX to line, 2=line RX to line TX, 3=PG to line")
		self.pcs_control_reg.mux_c = SignalInfo(self.pcs_control_reg, startbit=11, length=2, description="0|1=line RX to PC, 2=host tx to PC, 3=PG to PC")
		
		self.packet_generator_control_reg = MMDRegister(self, "Packet Generator Control Register", 3, 0xfd04)
		self.packet_generator_control_reg.enable_packet_generator = SignalInfo(self.packet_generator_control_reg, 3, description="Enable Packet Generator")
		self.packet_generator_control_reg.packet_burst = SignalInfo(self.packet_generator_control_reg, startbit=8, length=8, description="Packet Burst")


	def add_register(self, register: MMDRegister):
		self.registers.append(register)

	def read(self, device, register):
		return int(subprocess.check_output(["mdio","30be0000.ethernet-1","mmd",f"{self.mdio_addr}:{device}","raw",f"{register}"]), 16)
	# Mask does not seem to work
	def write(self, device, register, new_value, mask=0xFFFF):
		# Parameters raw REG [DATA[/MASK]]
		data = hex(new_value)
		if mask != 0xFFFF:
			data += f"/{hex(mask)}"
		mmd_args = ["mdio","30be0000.ethernet-1","mmd",f"{self.mdio_addr}:{device}","raw",f"{register}", data]
		#print(mmd_args)
		subprocess.check_call(mmd_args)
	
	def show_registers(self):
		for register in self.registers:
			register.fetch()
			print(register)


	@property
	def model(self):
		return (self.mdio_bus.read_mmd(self.mdio_addr, 1, 3) & 0x03F0) >> 4

	@property
	def revision(self):
		rev = self.read(1,3) & 0xF
		revisions = ("Z1", "A0", "A1", "A2")
		return revisions[rev] if 0 <= rev <= 3 else "UNKNOWN REVISION"

	@property
	def master(self):
		return (self.read(7, 0x8001) >> 14) & 0x0001

	@master.setter
	def master(self, act_master):
		reg=self.base_t1_pma_pmd_control_reg
		reg.fetch()
		if act_master:
			reg.data |= 0x4000
		else:
			reg.data &= 0xbfff

		print(hex(reg.data))
		reg.write()
		#print(hex(self.read(1, 0x834)))
		#print(self.write(device=1, register=0x834, new_value=0x4000 if act_master else 0, mask=0x4000))
		#print(hex(self.read(1, 0x834)))

	@property
	def aneg_enabled(self):
		return (self.read(7, 0x0200) & self.MRVL_88Q2112_AN_ENABLE) != 0

	@aneg_enabled.setter
	def aneg_enabled(self,value):
		regDataAuto = self.read(7, 0x8032)
		self.write(7, 0x8032, regDataAuto | 0x0001)
		self.write(7, 0x8031, 0x0013)
		self.write(7, 0x8031, 0x0a13)
		regDataAuto = (0xFC00 & regDataAuto) | 0x0012
		self.write(7, 0x8032, regDataAuto)
		self.write(7, 0x8031, 0x0016)
		self.write(7, 0x8031, 0x0a16)
		
		regDataAuto = self.read(7, 0x8032)
		regDataAuto = (0xFC00 & regDataAuto) | 0x64
		self.write(7, 0x8032, regDataAuto)
		self.write(7, 0x8031, 0x0017)
		self.write(7, 0x8031, 0x0a17)
		
		self.write(3, 0x800C, 0x0008)
		self.write(3, 0xFE04, 0x0016)

	@property
	def speed(self):
		if self.aneg_enabled:
			gigabit = (self.read(7, 0x801a) & 0x4000) >> 14
		else:
			gigabit = self.read(1, 0x834) & 1
		return 1000 if gigabit else 100	

	def soft_reset_for_gigabit(self):
		regDataAuto = 0

		if self.aneg_enabled:
			self.write(3, 0xFFF3, 0x0024)
        
		# enable low-power mode
		regDataAuto = self.read(1, 0x0000)
		self.write(1, 0x0000, regDataAuto | 0x0800)

		self.write(3, 0xFFF3, 0x0020)
		self.write(3, 0xFFE4, 0x000C)
		time.sleep(0.001)

		self.write(3, 0xffe4, 0x06B6)

		# disable low-power mode
		self.write(1, 0x0000, regDataAuto & 0xF7FF)
		time.sleep(0.001)

		self.write(3, 0xFC47, 0x0030)
		self.write(3, 0xFC47, 0x0031)
		self.write(3, 0xFC47, 0x0030)
		self.write(3, 0xFC47, 0x0000)
		self.write(3, 0xFC47, 0x0001)
		self.write(3, 0xFC47, 0x0000)

		self.write(3, 0x0900, 0x8000)

		self.write(1, 0x0900, 0x0000)
		self.write(3, 0xFFE4, 0x000C)


	@speed.setter
	def speed(self, megabits):
		if megabits == 1000:
			# disable tx
			self.write(1, 0x0900, 0x4000)

			# gigabit speed
			regData = self.read( 1, 0x0834)
			regData = (regData & 0xFFF0) | 0x0001
			self.write(1, 0x0834, regData)

			self.write(3, 0xFFE4, 0x07B5)
			self.write(3, 0xFFE4, 0x06B6)
			time.sleep(0.005); 

			self.write(3, 0xFFDE, 0x402F)
			self.write(3, 0xFE2A, 0x3C3D)
			self.write(3, 0xFE34, 0x4040)
			self.write(3, 0xFE4B, 0x9337)
			self.write(3, 0xFE2A, 0x3C1D)
			self.write(3, 0xFE34, 0x0040)
			self.write(7, 0x8032, 0x0064)
			self.write(7, 0x8031, 0x0A01)
			self.write(7, 0x8031, 0x0C01)
			self.write(3, 0xFE0F, 0x0000)
			self.write(3, 0x800C, 0x0000)
			self.write(3, 0x801D, 0x0800)
			self.write(3, 0xFC00, 0x01C0)
			self.write(3, 0xFC17, 0x0425)
			self.write(3, 0xFC94, 0x5470)
			self.write(3, 0xFC95, 0x0055)
			self.write(3, 0xFC19, 0x08D8)
			self.write(3, 0xFC1a, 0x0110)
			self.write(3, 0xFC1b, 0x0A10)
			self.write(3, 0xFC3A, 0x2725)    
			self.write(3, 0xFC61, 0x2627)    
			self.write(3, 0xFC3B, 0x1612)    
			self.write(3, 0xFC62, 0x1C12)    
			self.write(3, 0xFC9D, 0x6367)    
			self.write(3, 0xFC9E, 0x8060)    
			self.write(3, 0xFC00, 0x01C8)    
			self.write(3, 0x8000, 0x0000)    
			self.write(3, 0x8016, 0x0011)

			self.write(3, 0xFDA3, 0x1800)

			self.write(3, 0xFE02, 0x00C0)
			self.write(3, 0xFFDB, 0x0010)
			self.write(3, 0xFFF3, 0x0020)
			self.write(3, 0xFE40, 0x00A6) 

			self.write(3, 0xFE60, 0x0000)
			self.write(3, 0xFE04, 0x0008)
			self.write(3, 0xFE2A, 0x3C3D)
			self.write(3, 0xFE4B, 0x9334)

			self.write(3, 0xFC10, 0xF600)
			self.write(3, 0xFC11, 0x073D)
			self.write(3, 0xFC12, 0x000D)
			self.write(3, 0xFC13, 0x0010)
			
            # Set to default compliant mode setting
			self.write(3, 0xFDB8, 0x0000)
			self.write(3, 0xFD3D, 0x0000)

			MRVL_88Q2112_MODE_ADVERTISE = 0x0002
			self.write(1, 0x0902, MRVL_88Q2112_MODE_ADVERTISE)
			self.soft_reset_for_gigabit()
	
	
	# Get real time PMA link status
      # @param phyAddr bootstrap address of the PHY
      # @return true if link is up, false otherwise
	@property
	def link (self):
		if self.speed == 1000:
			link = self.read(3, 0x0901)
			link = self.read(3, 0x0901) #read twice, register latches low value
		else:
			link = self.read(3, 0x8109)
		return (link & 4) != 0
	
	def checklink(self):
		if self.speed == 1000:
			self.pcs_1000base_t1_status_reg_1.fetch()
			self.pcs_1000base_t1_status_reg_1.fetch()
			self.auto_neg_status_reg.fetch()
			self.local_rcvr_status_2_reg.fetch()
			return (\
				((self.pcs_1000base_t1_status_reg_1.data & 0x4) == 4) and \
				((self.auto_neg_status_reg.data & 0x3000) == 0x3000) and \
				((self.local_rcvr_status_2_reg .data & 0x10) == 0x10))
		else:
			self.reg_100base_t1_status_2.fetch()
			self.reg_100base_t1_status_1.fetch()
			self.reg_receiver_status.fetch()
			return (\
				((self.reg_100base_t1_status_2.data & 0x4) == 4) and \
				((self.reg_100base_t1_status_1.data & 0x3000) == 0x3000) and \
				((self.reg_receiver_status.data & 0x1) == 0x1))
	@property
	def auto_negotiation_status(self):
		pass

	@property
	def counter(self):
		reg = self.packet_checker_count_register
		reg.fetch()
		return (reg.data & 255, reg.data >> 8)

	@counter.setter
	def counter(self, enabled):
		reg = self.packet_checker_control_register
		reg.data = 0b0101 if enabled else 0
		reg.write()

	@property
	def generator(self,value):
		reg = self.packet_generator_control_reg
		reg.fetch()
		if value:
			reg.data |= (1 << 3) 
		else:
			reg.data &= ~(1 << 3)



	def __str__(self):
		return f"{self.mdio_addr} master:{self.master} speed {self.speed} link:{self.link} aneg:{self.aneg_enabled} rev:{self.revision}"

	def __repr__(self):
		return str(self)


def t1_setup_for_envtest():
    # Reset phys
    chip,nr=subprocess.check_output(("gpiofind", "A_ETH_nRST")).decode().strip().split()
    gpio_cmd=("gpioset", chip, f"{nr}=1")
    print(gpio_cmd)
    subprocess.check_call(gpio_cmd)
    time.sleep(0.1)
    subprocess.check_call(("gpioset", chip, f"{nr}=0"))
    time.sleep(0.1)

    phys=MDIOBus("3*").find_phys()
    if len(phys) == 0:
        raise RuntimeError(f"{len(phys)}/2 T1 phys found!")



    for i, phy in enumerate(phys):
        # Setup phys for rgmii-id
        if i == 0:
            phy.write(31,0x8001, 0xc000)
        else:
            phy.write(31,0x8001, 0x0000)

	    # reset phy for registers above
        phy.write(3,0x8000, 0x8000)
        phy.master = (i == 0) # set master on 1st phy
        phy.speed = 1000

    time.sleep(0.1)
    print(phys)
    
   # ip --human -s -s -c link show u

if __name__ == "__main__":
    t1_setup_for_envtest()
