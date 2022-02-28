<?PHP
$hostname_localhost="localhost";
$database_localhost="paecontactos";
$username_localhost="root";
$password_localhost="";

$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	$codigo = $_POST["codigo"];
	$imagen = $_POST["imagen"];
	$nombre = $_POST["nombre"];

	$bytesArchivo=base64_decode($imagen);

	$sql="INSERT INTO imagenes VALUES (?,?,?)";
	$stm=$conexion->prepare($sql);
	$stm->bind_param('sss',$codigo,$bytesArchivo,$nombre);
		
	if($stm->execute()){
		echo "registra";
	}else{
		echo "noRegistra";
	}
	
	mysqli_close($conexion);
?>