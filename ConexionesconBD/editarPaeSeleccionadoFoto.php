<?PHP
$hostname_localhost="localhost";
$database_localhost="paecontactos";
$username_localhost="root";
$password_localhost="";

$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	$codigo = $_POST["codigo"];
	$imagen = $_POST["imagen"];
	$nombre = $_POST["nombre"];
    $codigo2 = $_POST["codigo2"];


    $update="UPDATE  usuario SET codigo='{$codigo}',imagen='{$imagen}',nombre='{$nombre}' WHERE codigo='{$codigo2}'";

	$bytesArchivo=base64_decode($imagen);
	$stm=$conexion->prepare($update);
	$stm->bind_param('sss',$codigo,$bytesArchivo,$nombre);
		
	if($stm->execute()){
		echo "registra";
	}else{
		echo "noRegistra";
	}
	
	mysqli_close($conexion);
?>