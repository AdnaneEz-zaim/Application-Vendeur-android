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
    			  
    		    $sql=mysqli_prepare($db,"SELECT CURRENT_DATE() FROM DUAL;");
    		    mysqli_stmt_execute($sql);
    			mysqli_stmt_store_result($sql);
    			mysqli_stmt_bind_result($sql,$todaay);
    			while (mysqli_stmt_fetch($sql)) {
    				$toodaay=$todaay;
    	   		}

               $sql=mysqli_prepare($db,"SELECT * FROM reservation WHERE id_trajet = ? and date_reserve = ?");
    				
				mysqli_stmt_bind_param($sql,"is",$id_t,$toodaay);
				mysqli_stmt_execute($sql);
				mysqli_stmt_store_result($sql);
				mysqli_stmt_bind_result($sql,$idReserve,$quantite,$idClient,$idProduit,$idVendeur,$id_trajet,$id_position,$date);
				 while (mysqli_stmt_fetch($sql)) {
    				$result[]=['idReservation' => $idReserve,
    				"quantite"=> $quantite ,
    				"idProduit"=>$idProduit ,
    				"idVendeur"=>$idVendeur ,
    				"idClient"=>$idClient];
    				$j++;
	   			}

	   			for ($i=0; $i <= $j ; $i++) 
	   			{

	   				$sql1= mysqli_prepare($db,"SELECT * FROM logReserve WHERE id_Reserve = ? and VendeurConfirmer=0 and VendeurDecline=0");
			        mysqli_stmt_bind_param($sql1,"i",$result[$i]["idReservation"]);
			        mysqli_stmt_execute($sql1);
			        mysqli_stmt_store_result($sql1);
			        mysqli_stmt_bind_result($sql1,$idlogR,$id_Reserve,$idClient,$idVendeur,$clientClick,$clientDecline,$VendeurConfirmer,$VendeurDecline,$cmnt,$avis);
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
    				            $resulta[$h]["imag"]=$imag;
    				        }
						$h++;
			   		 }

			   	}
	   			/*
			   	for ($i=0; $i <= $h ; $h++){

							$sql=mysqli_prepare($db,"SELECT * FROM reservation WHERE id_Reservation = ?");
							mysqli_stmt_bind_param($sql,"i",$resulta[$i]["idReservation"]);
							mysqli_stmt_execute($sql);
							mysqli_stmt_store_result($sql);
							mysqli_stmt_bind_result($sql,$idReserve,$quantite,$idClient,$idProduit,$idVendeur,$id_trajet,$id_position,$date);
							while(mysqli_stmt_fetch($sql)){
								//$resulta[$i]["idReservation"] =$idReserve;
							    $resulta[$i]["quantite"]=$quantite;
							    $resulta[$i]["idProduit"]=$idProduit;
							    $resulta[$i]["idVendeur"]=$idVendeur;
							    $resulta[$i]["idClient"]=$idClient;
							}
							$sql=mysqli_prepare($db,"SELECT * FROM produit WHERE id_Produit= ?");
							mysqli_stmt_bind_param($sql,"i",$resulta[$i]["idProduit"]);
							mysqli_stmt_execute($sql);
							mysqli_stmt_store_result($sql);
							mysqli_stmt_bind_result($sql,$idP,$nom,$categorier,$prix,$imag);
						        while (mysqli_stmt_fetch($sql)) {
						            $resulta[$i]["nomProduit"]=$nom;
						            $resulta[$i]["imag"]=$imag;
						        }
						      }  
						      */
						      

			        echo json_encode($resulta);
				
			}
?>