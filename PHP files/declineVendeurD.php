<?php
 $mysqli= new PDO("mysql:host=localhost;dbname=id12877554_rz","id12877554_binome","012345678");
	$results["error"]=false;
	$results["messages"]=[];

      $idD=$_POST["idD"];
            
           /* $sql=$db->prepare("DELETE FROM logDemande WHERE id_Demande =:idD");
			$sql->execute([":idD" => $idD]);
			if (!$sql) {
				$results["error"]=true;
				$results["messages"]="Impossible de Rejeter";
				
			}else{
			    
    			$sql=$db->prepare("DELETE FROM demande WHERE id_Demande =:idD");
    			$sql->execute([":idD" => $idD]);
			}*/
			
				 $aa="UPDATE logDemande SET VendeurDecline = 1 WHERE id_Demande =".$idD."";
				 $sql=$mysqli->prepare($aa);
			//	$sql->bind_param("i",$idD);
				$sql->execute();
    				if (!$sql) {
    					$results["error"]=true;
    					$results["messages"]="Impossible de supperimer";
    					
    				}
    				

		    echo json_encode($results);
	 
	   
?>