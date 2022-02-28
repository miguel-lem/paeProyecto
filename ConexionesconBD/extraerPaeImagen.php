<?PHP
$hostname_localhost ="localhost";
$database_localhost ="paecontactos";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	if(isset($_GET["nombre"])){
		$nombre=$_GET["nombre"];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="select * from imagenes where nombre= '{$nombre}'";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$result["codigo"]=$registro['codigo'];
			$result["imagen"]=base64_encode($registro['imagen']);
			$result["nombre"]=$registro['nombre'];
			$json['usuario'][]=$result;
		}else{
			$resultar["codigo"]=0;
			$resultar["imagen"]='no registra';
			$result["nombre"]='no registra';
			$json['usuario'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['usuario'][]=$resultar;
		echo json_encode($json);
	}
?>