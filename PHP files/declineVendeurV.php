<?php
 $mysqli= new PDO("mysql:host=localhost;dbname=id12877554_rz","id12877554_binome","012345678");
	$results["error"]=false;
	$results["messages"]=[];
	if (isset($_POST)) {
		if ( !empty($_POST["idR"])) {

            $idR=$_POST["idR"];
            
            
           /* $sql=$db->prepare("DELETE FROM logReserve WHERE id_Reserve =:idR");
			$sql->execute([":idR" => $idR]);
			if (!$sql) {
				$results["error"]=true;
				$results["messages"]="Impossible de Rejeter";
				
			}else{
			    
    			$sql=$db->prepare("DELETE FROM reservation WHERE id_Reservation =:idR");
    			$sql->execute([":idR" => $idR]);
			}*/
				$aa="UPDATE logReserve SET VendeurDecline = 1 WHERE 'id_Reserve' =".$idR."";
			if( ! $sql = $mysqli->prepare($aa)){
				echo 'Error: ' . $mysqli->error;
			  return false;
			}
			else{
		//	$sql->bind_param("s",$idR);
			$sql->execute();
			if (!$sql) {
				$results["error"]=true;
				$results["messages"]="Impossible de confirmer";
				
			}

		    echo json_encode($results);
	    }
	}
?>