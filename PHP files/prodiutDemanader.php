<?php
	$db=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
	$idVendeur=$_POST['idVendeur'];
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
			         	$resulta[]=$result[$i];
			         	$sql=mysqli_prepare($db,"SELECT * FROM produit WHERE id_Produit= ?");
    					mysqli_stmt_bind_param($sql,"i",$resulta[$h]["idProduit"]);
    					mysqli_stmt_execute($sql);
    					mysqli_stmt_store_result($sql);
    					mysqli_stmt_bind_result($sql,$idP,$nom,$categorier,$prix,$imag);
    				        while (mysqli_stmt_fetch($sql)) {
    				            $resulta[$h]["nomProduit"]=$nom;
    				            $resulta[$h]["imag"]=$imag;
    				        }
    				    
    				    $h++;
			   		 }
			   	}

			     echo json_encode($resulta);
				
			}
?>