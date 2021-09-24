<?php

 $db= new PDO("mysql:host=localhost;dbname=id12877554_rz","id12877554_binome","012345678");
 $results["error"] = false;
 $results["message"] = [];
 //if(!empty($_POST)){

 	if(!empty($_POST['pseudo']) && !empty($_POST['password'])){

 			$pseudo= $_POST['pseudo'];//"vendeur@gmail.com";
 			$password=$_POST['password'];//"0123456789";
 			
 			$sql= $db->prepare("SELECT * FROM utilisateur WHERE email_u = :pseudo and passwd_ul= :passwod");
 			$sql->execute([":pseudo"=>$pseudo,":passwod"=>$password]);
 			$row = $sql->fetch(PDO::FETCH_OBJ);

 				if($row){
 						$results["error"]=false;
 						$results["id"]=$row->id_Utilisateur;
 						$results["pseudo"]=$row->email_u;
 						$results["nom"]=$row->nom_u;
 						$results["prenom"]=$row->prenom_u;
 						$results["tele"]=$row->tele_u;
 						$results["image"]=$row->profileImg;
 							$sql= $db->prepare("SELECT * FROM vendeur WHERE id_Utilisateur = :x");
                 			$sql->execute([":x"=>$results["id"]]);
                 			$row = $sql->fetch(PDO::FETCH_OBJ);
                 			if($row){
         						$results["idV"]=$row->id_Vendeur;
         						$results["solde"]=$row->solde_v;
                 			}
     						else{
             					$results["error"]=true;
             					$results["message"]="email ou mot de passe incorrect";
             				}

 				}
 				else{
 					$results["error"]=true;
 						$results["message"]="email ou mot de passe incorrect";
 				}

 	}
 	else{
		$results["error"]=true;
		 $results["message"]="Veullez remplire tous les champs";
 	}
//echo"".$results["id"]."    ".$results["tele"]."    image".$results["image"];

echo json_encode($results);

 //}

?>