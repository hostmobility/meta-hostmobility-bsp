get_mx4_type_from_machine() {
    echo $1 | sed -e "s/^mx4-//"
}
