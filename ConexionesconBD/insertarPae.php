<?PHP
$hostname="localhost";
$database="paecontactos";
$username="root";
$password="";

$json=array();

	if(isset($_GET["nombre"])&&isset($_GET["direccion"])&&isset($_GET["email"])&&isset($_GET["telefono"])&&isset($_GET["id"])){
        $nombre=$_GET["nombre"];
        $direccion=$_GET["direccion"];
        $email=$_GET["email"];
        $telefono=$_GET["telefono"];
        $id=$_GET["id"];
        //$conexion =mysqli_connect($hostname,$username,$password,$database);
        $conexion = new mysqli($hostname,$username,$password,$database);

        if ($conexion->connect_errno) {
            echo "Fallo al conectar a MySQL: (" . $mysqli->connect_errno . ") " . $conexion->connect_error;
        }


        $insert="INSERT INTO usuario (nombre, direccion, email, telefono, id ) VALUES('{$nombre}','{$direccion}','{$email}','{$telefono}','{$id}')";
        $resultado_insert = mysqli_query($conexion,$insert);
        if($resultado_insert){
            $consulta="SELECT * FROM usuario WHERE id = '{$id}'";
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