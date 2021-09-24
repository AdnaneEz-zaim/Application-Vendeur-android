<?php
$idVendeur=3;//$_GET['idVendeur'];
$db=mysqli_connect("localhost","id12877554_binome","012345678","id12877554_rz");
$sql=mysqli_prepare($db,"SELECT * FROM avoir WHERE id_Vendeur= ? and quantite_produit < 50");
mysqli_stmt_bind_param($sql,"i",$idVendeur);
mysqli_stmt_execute($sql);
mysqli_stmt_store_result($sql);
mysqli_stmt_bind_result($sql,$id_avoir,$quantite_produit,$idProduit,$idVendeur);
$j=0;
while(mysqli_stmt_fetch($sql)){
	$resulta[]=[
        'quantite' => $quantite_produit,
        'idProduit' => $idProduit,
        'idVendeur' => $idVendeur	];
    $j++;
}
for($i=0;$i<=$j;$i++){
	$sql=mysqli_prepare($db,"SELECT * FROM produit WHERE id_Produit= ?");
mysqli_stmt_bind_param($sql,"s",$resulta[$i]["idProduit"]);
mysqli_stmt_execute($sql);
mysqli_stmt_store_result($sql);
mysqli_stmt_bind_result($sql,$idP,$nom,$categorier,$prix,$imag);
        while (mysqli_stmt_fetch($sql)) {
            $resulta[$i]["nomProduit"]=$nom;
            $resulta[$i]["categorier"]=$categorier;
			$resulta[$i]["imag"]=$imag;
    }
}
 echo json_encode($resulta);

?>