<section id="shop">
    <div class="container">
        <h2>The Shop</h2>
        <div class="row">
            <?php
                //$jsonArray beinhaltet alle waren
                //diese schleife läuft alle waren durch und gibt die informationen aus
                for($i=0; $i<count($jsonArray);$i++){
                    echo '<div class="col-xs-12 col-sm-6 col-md-4">';
                    echo "<h3>".$jsonArray[$i]['titel']."</h3>";
                    echo '<img style="width:200px;height:200px;" class=" rounded-circle center" src="FHTW_Merchandise/'
                            .$jsonArray[$i]['bild'].'" alt="'
                            .$jsonArray[$i]['titel'].'">';
                    echo "<p>".$jsonArray[$i]['beschreibung']."</p>";
                    echo '<div class="row">';
                    if(!$_SESSION['asAdmin']){
                        //durch '+' und '-' wird diese seite nochmal neu aufgerufen 
                        //und die ID mitgeliefert und ob es ein plus oder minus war
                        echo '<div class="col-4"><a href="index.php?menu=shop&plus='
                                .$jsonArray[$i]['id'].'"><button>+</button></a></div>';
                        echo '<div class="col-4"><a href="index.php?menu=shop&minus='
                                .$jsonArray[$i]['id'].'"><button>-</button></a></div>';
                    }
                    echo '<div class="col-4">'.$jsonArray[$i]['preis'].'</div>';
                    echo '</div></div>';
                }
                    //var_dump($_SESSION['warenkorb']);
            ?>
        </div>
    </div>
</section>