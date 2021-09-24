<?php
    $db =mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
    if ($db) {
        $img=$_POST["img"];
        $idU=$_POST["idU"];
        $name="vendeur".$_POST["idU"];
		$uploadPath="https://rzbusinessma.000webhostapp.com/profileImgs/Vendeurs/".$name.".png";
        $sql="Update utilisateur set profileImg='$uploadPath' where id_Utilisateur='$idU'";

        if (mysqli_query($db, $sql)) {
            file_put_contents("/storage/ssd3/554/12877554/public_html/profileImgs/Vendeurs/".$name.".png", base64_decode($img));
            echo json_encode(array('response'=>"Image uploaded"));
        }else{
            echo json_encode(array('response'=>"Image could not be uploaded"));
        }

    }else{
        echo json_encode(array('response'=>"Image could not be uploaded!"));
    }
    mysqli_close($db);
?>