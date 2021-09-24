<?php
	$mysqli=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
				if ($mysqli->connect_errno) {
				  echo "Impossible de se connecter à MySQL: " . $mysqli->connect_error;
				  exit();
				}

		
		$idVen=4;//$_GET['idVendeur'];
		$date=getDate();
        $days=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
        $dayOfWeek = strtolower($days[$date["wday"]]);
        /////////////////////////// la fonction de court destance entre le vendeur et depot \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

 				
 				function getNearDepot($mysqli,$latitude,$longitude)
 				{
 				     $locations=array();
					        $sql= mysqli_prepare($mysqli,"SELECT * FROM depot");
					        mysqli_stmt_execute($sql);
					        mysqli_stmt_store_result($sql);
							mysqli_stmt_bind_result($sql,$idDep,$nomdepot,$id_Agent,$id_position,$teleDepot);
					        while(mysqli_stmt_fetch($sql)) {
					            	$details=mysqli_prepare($mysqli,"SELECT * FROM position where id_pos = ?");

										mysqli_stmt_bind_param($details,"i",$id_position);
										mysqli_stmt_execute($details);
										mysqli_stmt_store_result($details);
										mysqli_stmt_bind_result($details,$idpos,$x,$y);
								        while (mysqli_stmt_fetch($details)) {
								           $locations [] =["id"=>$idDep,"lat"=>$x,"lng"=>$y];
								        }
					           
					        }
					        
					        $base_location = array(
					          'lat' => $latitude,
					          'lng' => $longitude
					        );
					        
					        $distances = array();
                            $key="";
					        foreach ($locations as $key => $location)
					        {
					          $a = $base_location['lat'] - $location['lat'];
					          $b = $base_location['lng'] - $location['lng'];
					          $distance = sqrt(($a**2) + ($b**2));
					          $distances[$key] = $distance;
					        }
					        
					        asort($distances);
					        
					        $closest = $locations[key($distances)];
					        
					        return $closest['id'];
			}
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        $tt="SELECT * FROM vendeurPlan WHERE id_Vendeur = ? and day =?";
			if( ! $stml = $mysqli->prepare($tt) ){
			  echo 'Error: ' . $mysqli->error;
			  return false;
			} else {
			  $stml->bind_param("is",$idVen,$dayOfWeek);
			  $stml->execute();
			  $stml->bind_result($id_VendeurPlan,$id_Vendeur,$id_Trajet,$day);
			  $stml->fetch();
			  $id_t=$id_Trajet;
			  $stml->store_result();
			  $stml->close();
				if( $id_t){
				$aa="SELECT * FROM trajet WHERE id_Trajet = ?";
				if( ! $sql=$mysqli->prepare($aa)){
				 echo 'Error: ' . $mysqli->error;
				return false;
				}
				else{
				$sql->bind_param("i",$id_Trajet);
				$sql->execute();
			  	$sql->bind_result($id_Trajet1,$pos_debut,$pos_fin);
			 	 $sql->fetch();
			 	 $sql->store_result();
			 	 $sql->close();
				if (!$sql) {
					$results["error"]=true;
					$results["messages"]="il ya un error dans la recuperation de la position de trajet";
					}

				$nn= mysqli_prepare($mysqli,"SELECT * FROM position WHERE id_pos = ?");
				        mysqli_stmt_bind_param($nn,"i",$pos_debut);
				        mysqli_stmt_execute($nn);
				        mysqli_stmt_store_result($nn);
						mysqli_stmt_bind_result($nn,$id_post,$x,$y);
				        while (mysqli_stmt_fetch($nn)) {
							$resulta=['centre_position_x' => $x,'centre_position_y'=>$y];
				    }

				    


					   $idDepot=getNearDepot($mysqli,$x,$y);

					   $sql=mysqli_prepare($mysqli,"SELECT * FROM depot WHERE id_Depot= ?");
						mysqli_stmt_bind_param($sql,"i",$idDepot);
						mysqli_stmt_execute($sql);
						mysqli_stmt_store_result($sql);
						mysqli_stmt_bind_result($sql,$idDep,$nomdepot,$id_Agent,$id_positionD,$teleDepot);
				        while (mysqli_stmt_fetch($sql)) {
				        	
				            $resulta["nomDepot"]=$nomdepot;
				            $resulta["telephone"]=$teleDepot;

				            $nn= mysqli_prepare($mysqli,"SELECT * FROM position WHERE id_pos = ?");
		                     mysqli_stmt_bind_param($nn,"i",$id_positionD);
		                     mysqli_stmt_execute($nn);
		                     mysqli_stmt_store_result($nn);
		                     mysqli_stmt_bind_result($nn,$id_post,$xd,$yd);
		                     while (mysqli_stmt_fetch($nn)) {
                                  $resulta["depot_x"]=$xd;
                                  $resulta["depot_y"]=$yd;
                           		} 
                    		$nn->close();
				        }




				}
				echo json_encode($resulta);
				}
				else{
					$results["error"]=true;
					$results["messages"]="il ya un erruer concerne la recuperation de id de trajet de table plan vendeur";
					echo json_encode($results);
				}
			}

?>