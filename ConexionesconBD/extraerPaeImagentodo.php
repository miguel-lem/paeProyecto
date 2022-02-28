<?PHP
$hostname_localhost ="localhost";
$database_localhost ="paecontactos";
$username_localhost ="root";
$password_localhost ="";

$json=array();
			
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="SELECT * FROM imagenes";
		$resultado=mysqli_query($conexion,$consulta);
			
		
            while($registro=mysqli_fetch_array($resultado)){
                $result["codigo"]=$registro['codigo'];
                $result["imagen"]=base64_encode($registro['imagen']);
                $result["nombre"]=$registro['nombre'];
                $json[]=$result;
            }
						
       
        mysqli_close($conexion);
        echo json_encode($json);

?>