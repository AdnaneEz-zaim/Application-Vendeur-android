<?php
	$db=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
	$idVendeur=$_GET['idVendeur'];
	$j=0;
	$h=0;
	$nombreReservation=0;
	$nombreDemande=0;
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
    			
    		    $sql=mysqli_prepare($db,"SELECT CURRENT_DATE() FROM DUAL;");
    		    mysqli_stmt_execute($sql);
    			mysqli_stmt_store_result($sql);
    			mysqli_stmt_bind_result($sql,$todaay);
    			while (mysqli_stmt_fetch($sql)) {
    				$toodaay=$todaay;
    	   		}
///////////////////////////////////////// OMBRE RESERVATION  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
               $sql=mysqli_prepare($db,"SELECT * FROM reservation WHERE id_trajet = ? and date_reserve = ?");	
				mysqli_stmt_bind_param($sql,"is",$id_t,$toodaay);
				mysqli_stmt_execute($sql);
				mysqli_stmt_store_result($sql);
				mysqli_stmt_bind_result($sql,$idReserve,$quantite,$idClient,$idProduit,$idVende,$id_trajet,$id_position,$date);
				 while (mysqli_stmt_fetch($sql)) {
    				$result[]=['idReservation' => $idReserve];
    				$j++;
	   			}
           
	   			for ($i=0; $i <= $j ; $i++) 
	   			{
	   				$sql1= mysqli_prepare($db,"SELECT * FROM logReserve WHERE id_Reserve = ? and VendeurConfirmer=0 and VendeurDecline=0");
			        mysqli_stmt_bind_param($sql1,"i",$result[$i]["idReservation"]);
			        mysqli_stmt_execute($sql1);
			        mysqli_stmt_store_result($sql1);
			        mysqli_stmt_bind_result($sql1,$idlogR,$id_Reserve,$idClient,$idVede,$clientClick,$clientDecline,$VendeurConfirmer,$VendeurDecline,$cmnt,$avis);
			        while (mysqli_stmt_fetch($sql1))
			         {
			         	$nombreReservation++;
			   		 }
			   	}

///////////////////////////////////////// OMBRE DEMANDES  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

				$sql=mysqli_prepare($db,"SELECT * FROM demande WHERE id_trajet= ?");
				mysqli_stmt_bind_param($sql,"i",$id_t);
				mysqli_stmt_execute($sql);
				mysqli_stmt_store_result($sql);
				mysqli_stmt_bind_result($sql,$idDemande,$quantite,$idClient,$idVendr,$idProduit,$id_trajet,$id_position);
				 while (mysqli_stmt_fetch($sql)) {
    				$result[]=['idDemande' => $idDemande];
    				$h++;
	   			}
                 
	   			for ($i=0; $i <= $h ; $i++) 
	   			{

	   				$sql1= mysqli_prepare($db,"SELECT * FROM logDemande WHERE id_Demande = ? and VendeurConfirmer=0 and VendeurDecline=0");
			        mysqli_stmt_bind_param($sql1,"i",$result[$i]["idDemande"]);
			        mysqli_stmt_execute($sql1);
			        mysqli_stmt_store_result($sql1);
			        mysqli_stmt_bind_result($sql1,$idlogD,$idDemande,$idClient,$idVdeur,$clientClick,$VendeurConfirmer,$VendeurDecline,$cmnt,$avis);
			        while (mysqli_stmt_fetch($sql1))
			         {
			         	$nombreDemande++;
			   		 }
			   	}
			   	$sql=mysqli_prepare($db,"SELECT count(*) FROM avoir WHERE id_Vendeur= ? and quantite_produit < 50");
				mysqli_stmt_bind_param($sql,"i",$idVendeur);
				mysqli_stmt_execute($sql);
				mysqli_stmt_store_result($sql);
				mysqli_stmt_bind_result($sql,$produitS);
				 while (mysqli_stmt_fetch($sql));
				$rs=['nombreReservation' => $nombreReservation,
    				"nombreDemande"=> $nombreDemande,
    				"produitS"=>$produitS];


			        echo json_encode($rs);
				
			}
?>