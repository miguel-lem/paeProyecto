<?PHP
$hostname="localhost";
$database="paecontactos";
$username="root";
$password="";

$json=array();

	if(isset($_GET["nombre"])&&isset($_GET["direccion"])&&isset($_GET["email"])&&isset($_GET["telefono"])&&isset($_GET["id"])&&isset($_GET["codigo"])){
        $nombre=$_GET["nombre"];
        $direccion=$_GET["direccion"];
        $email=$_GET["email"];
        $telefono=$_GET["telefono"];
        $id=$_GET["id"];
        $codigo=$_GET['codigo'];
        
        $conexion = new mysqli($hostname,$username,$password,$database);

        if ($conexion->connect_errno) {
            echo "Fallo al conectar a MySQL: (" . $mysqli->connect_errno . ") " . $conexion->connect_error;
        }
        
        $update="UPDATE  usuario SET nombre='{$nombre}',direccion='{$direccion}',email='{$email}',telefono='{$telefono}',id='{$id}' WHERE id='{$codigo}'";
        
        $resultado_insert = mysqli_query($conexion,$update);
        if($resultado_insert){
            $consulta="SELECT * FROM usuario WHERE nombre = '{$nombre}'";
            $resultado = mysqli_query($conexion,$consulta);
            if($registro=mysqli_fetch_array($resultado)){
                $json['usuario'][]=$registro;
            }
            mysqli_close($conexion);
	        echo json_encode($json);
        }else{
            $resulta["nombre"]='no registra';
            $resulta["direccion"]='no registra';
            $resulta["email"]='no registra';
            $resulta["telefono"]='no registra';
            $resulta["id"]='no registra';
            $json['usuario'][]=$resulta;
            echo json_encode($json);
        }
	}else{
        $resulta["nombre"]='no registra';
        $resulta["direccion"]='no registra';
        $resulta["email"]='no registra';
        $resulta["telefono"]='no registra';
        $resulta["id"]='no registra';
        $json['usuario'][]=$resulta;
        echo json_encode($json);
	}
	
?>