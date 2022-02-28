<?PHP
$hostname="localhost";
$database="paecontactos";
$username="root";
$password="";

$conexion = new mysqli("localhost", "root", "", "paecontactos");
if ($conexion->connect_errno) {
    echo "Fallo al conectar a MySQL: (" . $mysqli->connect_errno . ") " . $conexion->connect_error;
}

$datos=mysqli_query($conexion,"SELECT * FROM usuario");
$resultado=mysqli_fetch_all($datos,MYSQLI_ASSOC);
							
mysqli_close($conexion);
echo json_encode($resultado);

?>