<section id="warenkorb">
    <div class="container">
        <h2>Basket</h2>
        <div class="row">
            <?php
                //warenkorb wird sortiert da eine ware mehrmals hinzugefügt werden kann
                if(!empty($_SESSION['warenkorb'])){
                    sort($_SESSION['warenkorb']);
                }else{
                    echo '<p>Basket is empty!</p>';
                    echo '<a href="index.php?menu=shop"><p>Click here to go to the shop.</p></a>';
                }
                //alle waren die angeklickt wurden werden angezeigt
                echo '<div class="row">';
                for($i=0; $i<count($_SESSION['warenkorb']);$i++){
                    
                    echo '<div class="col-xs-12 col-sm-6 col-md-4">';
                    //da wir im warenkorb die ID abgespeichert haben
                    //und das Array bei 0 beginnt müssen wir hier -1 einkalkulieren
                    echo "<h3>".$jsonArray[$_SESSION['warenkorb'][$i]-1]['titel']."</h3>";
                    echo '<img style="width:200px;height:200px;" class="rounded-circle center" src="FHTW_Merchandise/'
                            .$jsonArray[$_SESSION['warenkorb'][$i]-1]['bild']
                            .'" alt="'.$jsonArray[$_SESSION['warenkorb'][$i]]['titel'].'">';
                    echo "<p>".$jsonArray[$_SESSION['warenkorb'][$i]-1]['beschreibung']."</p>";
                    echo '<div class="row">';
                    //falls eine ware öfters im warenkorb ist
                    //wird dieser hier zusammengezählt und in $j gespeichert
                    //und ausgegeben
                    for($j=1; $i<count($_SESSION['warenkorb']);$i++){
                        if(($i+1<count($_SESSION['warenkorb']))&&
                                $_SESSION['warenkorb'][$i]==$_SESSION['warenkorb'][$i+1]){
                            $j++;
                        } else {
                            echo '<div class="col-4"><p>'.$j.'X</p></div>';
                            break;
                        }
                    }
                    echo '<div class="col-4">'.$jsonArray[$_SESSION['warenkorb'][$i]-1]['preis'].'</div>';
                    echo '<div class="col-4"><a href="index.php?menu=warenkorb&minus='
                    .$jsonArray[$_SESSION['warenkorb'][$i]-1]['id']
                            .'"><button>-</button></a></div>';
                    echo '</div></div>';
                }
                echo '</div>';
                    //var_dump($_SESSION['warenkorb']);
            ?>
        </div>
    </div>
</section>