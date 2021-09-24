<?php
$mysqli=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
if ($mysqli->connect_errno) {
  echo "Impossible de se connecter à MySQL: " . $mysqli->connect_error;
  exit();
}

		$qnt=$_GET['qnt'];
		$idProd=$_GET['idProd'];
		$idVen=$_GET['idVen'];//il reste pour moi de recupere de Myrequest
		$idD=$_GET['idD'];
		$results["error"]=false;
		$results["messages"]=[];
	

           $tt="SELECT * FROM logDemande WHERE id_Demande = ?";
			if( ! $stml = $mysqli->prepare($tt) ){
			  echo 'Error: ' . $mysqli->error;
			  return false; // throw exception, die(), exit, whatever...
			} else {
			  $stml->bind_param("i",$idD);
			  $stml->execute();
			  $stml->bind_result($id_logD,$id_Demande,$ClientClick,$VendeurConfirmer,$VendeurDecline,$commentD,$avisD);
			  $stml->fetch();
			  $reponce=$VendeurConfirmer;
			  $stml->store_result();
			  $stml->close();///////////!!!!!!!!!!!!!!pour l'execution a la dieuxieme fois 
				if(! $reponce){
			
			
				$mm="SELECT * FROM avoir WHERE id_Produit= ? and id_Vendeur= ?";
				if( ! $stmt = $mysqli->prepare($mm) ) {
				  echo 'Error: ' . $mysqli->error;
				  return false; // throw exception, die(), exit, whatever...
				} else {
				  $stmt->bind_param("ii", $idProd, $idVen);
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
				
				
				
				$newquan=$qq - $qnt;
				if($newquan >= 0){
				    	$aa="UPDATE logDemande SET VendeurConfirmer = true WHERE id_Demande = ?";
				if( ! $sql=$mysqli->prepare($aa)){
				 echo 'Error: ' . $mysqli->error;
				return false; // throw exception, die(), exit, whatever...
				}
			
				$sql->bind_param("i",$idD);
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
				$sth->bind_param('iii',$newquan,$idProd,$idVen);
				//[":newquan" => $newquan,":idProd"=>$idProd,":idVen"=>$idVen]
				$sth->execute();
				if (!$sth) {
					$results["error"]=true;
					$results["messages"]="il ya un erreurs dans vos produits";
					
				}
				echo json_encode($results);
				}
			
				}
				else{
					$results["error"]=true;
					$results["messages"]="la quantite_produit n'est pas insefusente";
					echo json_encode($results);
				}
				}
			//	}
				}
				else{
					$results["error"]=true;
					$results["messages"]="le produits et dejat confirmer";
					echo json_encode($results);
				}
			}
	   
?>