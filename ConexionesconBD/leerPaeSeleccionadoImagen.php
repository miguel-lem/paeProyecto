<?PHP
$hostname_localhost ="localhost";
$database_localhost ="paecontactos";
$username_localhost ="root";
$password_localhost ="";

$json=array();
			
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		if(isset($_GET["nombre"])){
            $nombre=$_GET["nombre"];
                    
            $conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
    
            $consulta="SELECT * FROM imagenes WHERE nombre= '{$nombre}'";
            $resultado=mysqli_query($conexion,$consulta);
			
		
            while($registro=mysqli_fetch_array($resultado)){
                $result["codigo"]=$registro['codigo'];
                $result["imagen"]=base64_encode($registro['imagen']);
                $result["nombre"]=$registro['nombre'];
                $json[]=$result;
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