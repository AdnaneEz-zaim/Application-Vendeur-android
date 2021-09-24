<?php
$mysqli=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
if ($mysqli->connect_errno) {
  echo "Impossible de se connecter à MySQL: " . $mysqli->connect_error;
  exit();
}

		$qnt=$_POST['qnt'];
		$idProd=$_POST['idProd'];
		$idVen=$_POST['idVen'];
		$idR=$_POST['idR'];
		$results["error"]=false;
		$results["messages"]=[];
	

            

		
			
			$mm="SELECT * FROM avoir WHERE id_Produit= ? and id_Vendeur= ?";
			if( ! $stmt = $mysqli->prepare($mm) ) {
			  echo 'Error: ' . $mysqli->error;
			  return false; // throw exception, die(), exit, whatever...
			} else {
			  $stmt->bind_param("ss", $idProd, $idVen);
			// exécuter la requête  [":idp" => $idProd,":idv" => $idVen]
			$stmt->execute();
			//$sql->execute([":idD" => $idD]);
			// associer la colonne du jeu de résultats à une variable
			$stmt->bind_result($id_Avoir,$quantite_produit,$id_Produit,$id_Vendeur);
			// récupérer la valeur
			$stmt->fetch();
			$qq=$quantite_produit;
			$stmt->store_result();
		

			/* free result */
			//$stmt->free_result();

			/* close statement */
			$stmt->close();///////////!!!!!!!!!!!!!!baxe yla brit n executer lmara 2
			
			
			
			$newquan=$qq - intval($qnt);
		    echo $newquan." ".gettype($newquan);
			if($newquan >= 0){	
			    	$aa="UPDATE logReserve SET VendeurConfirmer = 1 WHERE 'id_Reserve' = ?";
			if( ! $sql = $mysqli->prepare($aa)){
				echo 'Error: ' . $mysqli->error;
			  return false; // throw exception, die(), exit, whatever...
			}
			else{
			$sql->bind_param("s",$idR);
			$sql->execute();
			if (!$sql) {
				$results["error"]=true;
				$results["messages"]="Impossible de confirmer";
				
			}
			$nn="UPDATE avoir SET quantite_produit= ? WHERE id_Produit= ? and id_Vendeur= ?";
			if( ! $sth = $mysqli->prepare($nn)){
				echo 'Error: ' . $mysqli->error;
			  return false; // throw exception, die(), exit, whatever...
			}
			else{
			$sth->bind_param('sss',$newquan,$idProd,$idVen);
			//[":newquan" => $newquan,":idProd"=>$idProd,":idVen"=>$idVen]
			$sth->execute();
			if (!$sth) {
				$results["error"]=true;
				$results["messages"]="il ya un erreurs dans vos produits";
				
			}
		    echo json_encode($results);
			}
			}
			}
			}
			
	   
?>