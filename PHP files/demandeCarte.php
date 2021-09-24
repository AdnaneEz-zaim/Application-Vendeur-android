<?php
	$db=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
	$idVendeur=$_GET['idVendeur'];
	$j=0;
	$h=0;
	$resulta=array();
		 $date=getDate();
		    $days=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
		    $dayOfWeek = strtolower($days[$date["wday"]]);
		    
		    $tt="SELECT * FROM vendeurPlan WHERE id_Vendeur = ? and day=? ;";
			if( ! $stml = mysqli_prepare($db,$tt) ){
			  echo 'Error: ' . $mysqli->error;
			  return false;
			} else {
    			  mysqli_stmt_bind_param($stml,"ss",$idVendeur,$dayOfWeek);
    			  mysqli_stmt_execute($stml);
    			  mysqli_stmt_store_result($stml);
    			  mysqli_stmt_bind_result($stml,$id_VendeurPlan,$id_Ven,$id_TrajetVendeur,$day);
    			  while(mysqli_stmt_fetch($stml)){
    			    $id_t=$id_TrajetVendeur;
    			  }
    			  
    	   		$sql=mysqli_prepare($db,"SELECT * FROM demande WHERE id_trajet= ?");
				mysqli_stmt_bind_param($sql,"i",$id_t);
				mysqli_stmt_execute($sql);
				mysqli_stmt_store_result($sql);
				mysqli_stmt_bind_result($sql,$idDemande,$quantite,$idClient,$idVendeur,$idProduit,$id_trajet,$id_position);
				 while (mysqli_stmt_fetch($sql)) {
    				$result[]=['idDemande' => $idDemande,
			    				"quantite"=> $quantite ,
			    				"idProduit"=>$idProduit ,
			    				"idVendeur"=>$idVendeur ,
			    				"idClient"=>$idClient];

    				$nn= mysqli_prepare($db,"SELECT * FROM position WHERE id_pos = ?");
                     mysqli_stmt_bind_param($nn,"i",$id_position);
                     mysqli_stmt_execute($nn);
                     mysqli_stmt_store_result($nn);
                     mysqli_stmt_bind_result($nn,$id_post,$x,$y);
                     while (mysqli_stmt_fetch($nn)) {
                                $result[$j]["centre_position_x"]=$x;
                                $result[$j]["centre_position_y"]=$y;
                           } 
                    $nn->close();
    				$qq= mysqli_prepare($db,"SELECT * FROM client WHERE id_Client = ?");
                     mysqli_stmt_bind_param($qq,"i",$idClient);
                     mysqli_stmt_execute($qq);
                     mysqli_stmt_store_result($qq);
                     mysqli_stmt_bind_result($qq,$id_Client,$adresseC,$idUtilisateur);
                     mysqli_stmt_fetch($qq); 
                      $qq->close();
                              
                     $aa= mysqli_prepare($db,"SELECT * FROM utilisateur WHERE id_Utilisateur = ?");
                     mysqli_stmt_bind_param($aa,"i",$idUtilisateur);
                     mysqli_stmt_execute($aa);
                     mysqli_stmt_store_result($aa);
                     mysqli_stmt_bind_result($aa,$id_Utilisateur,$email_u,$nomU,$prenom,$passwd,$tele,$id_pos,$imageClient);
                     while (mysqli_stmt_fetch($aa)) {
                            
                                $result[$j]["nomClient"]=$nomU." ".$prenom;
                                $result[$j]["imageClient"]=$imageClient;
                                $result[$j]["telephone"]=$tele;
                           } 
                    $aa->close();
    				$j++;
	   			}

	   			for ($i=0; $i <= $j ; $i++) 
	   			{

	   				$sql1= mysqli_prepare($db,"SELECT * FROM logDemande WHERE id_Demande = ? and VendeurConfirmer=0 and VendeurDecline=0");
			        mysqli_stmt_bind_param($sql1,"i",$result[$i]["idDemande"]);
			        mysqli_stmt_execute($sql1);
			        mysqli_stmt_store_result($sql1);
			        mysqli_stmt_bind_result($sql1,$idlogD,$idDemande,$idClient,$idVendeur,$clientClick,$VendeurConfirmer,$VendeurDecline,$cmnt,$avis);
			        while (mysqli_stmt_fetch($sql1))
			         {
			         	$resulta[$h]=$result[$i];
			         	$sql=mysqli_prepare($db,"SELECT * FROM produit WHERE id_Produit= ?");
    					mysqli_stmt_bind_param($sql,"i",$resulta[$h]["idProduit"]);
    					mysqli_stmt_execute($sql);
    					mysqli_stmt_store_result($sql);
    					mysqli_stmt_bind_result($sql,$idP,$nom,$categorier,$prix,$imag);
    				        while (mysqli_stmt_fetch($sql)) {
    				            $resulta[$h]["nomProduit"]=$nom;
    				            $resulta[$h]["categorier"]=$categorier;
    				        }
						$h++;
			   		 }
			   		 
			   	    

			   	}

			     echo json_encode($resulta);
				
			}
?>