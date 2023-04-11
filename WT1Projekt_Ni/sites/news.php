<section id="news">
    <div class="container">
        <h2>News</h2>
        <?php
            if(isset($_POST['title'])&&isset($_POST['text'])
                    &&isset($_FILES["fileToUpload"])&&empty($_POST['titleToDel'])){
                $t=time();
                //title, text und pic bekommen alle bei new den selben namen und zwar die zeit im richtigen format
                $name = date("Ymd_His",$t);
                //nur der pfad ist anders
                $fileTitle = fopen("data/news/header/".$name.".txt", "w");
                $fileText = fopen("data/news/text/".$name.".txt", "w");
                fwrite($fileTitle, $_POST['title']);
                fwrite($fileText, $_POST['text']);
                //target_file ist das ziel wo das pic abgespeichert werden soll
                $target_file = "data/news/pics/". basename($_FILES["fileToUpload"]["name"]);
                //das pic wird in targetfile gespeichert
                move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file);
                //imageFileType der datentyp vom pic da jpeg oder png sein könnte
                $imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
                //dann wird noch der name vom pic geändert
                rename("data/news/pics/".$_FILES["fileToUpload"]["name"], "data/news/pics/"
                        .$name.'.'.$imageFileType);
            }
            //beim anzeigen werden die jeweiligen ordner gescannt
            $header = scandir("data/news/header/");
            $text = scandir("data/news/text/");
            $pics = scandir("data/news/pics/");
            $a=-10;
            //falls ein news eintrag gelöscht werden soll wird durchsucht ob
            //titleToDel einen titel beinhaltet der existiert
            if(isset($_POST['titleToDel'])){
                //beim durchsuchen beginnen wir bei 2 da die ersten 2 files . und .. sind
                for($i=2;$i<count($header);$i++){
                    //dann wird die Pos von dem zu löschenden news eintrag in $a gespeichert
                    if(file_get_contents("data/news/header/".$header[$i])==$_POST['titleToDel']){
                        $a=$i;
                    }
                }
                if($a>0){
                    //dieser wird dann gelöscht
                    unlink("data/news/header/".$header[$a]);
                    unlink("data/news/text/".$text[$a]);
                    unlink("data/news/pics/".$pics[$a]);
                }
            }
            //da aus dem ordner was entfernt wurde muss neu gescannt werden
            $header = scandir("data/news/header/");
            $text = scandir("data/news/text/");
            $pics = scandir("data/news/pics/");
            $j=2;
            echo '<div class="row">';
            //2 wegen . und ..
            for($i=2;$i<count($header);$i++){
                echo '<div class="col-xs-12 col-sm-6 col-md-4">';
                echo "<h3>".file_get_contents("data/news/header/".$header[$i])."</h3>";
                echo '<img style="width:150px;height:150px" src="data/news/pics/'
                .$pics[$j].'" alt="'.file_get_contents("data/news/header/".$header[$i]).'">';
                //$j wird nur dann erhöht wenn ein pic angezeigt wird da es mehrere pics pro eintrag geben kann
                $j++;
                while($j<count($pics)){
                    //mit explore wird der name in ein array gespeicht wo '_' die trennung ist
                    //da wir vorher $j erhöht haben wird nun das neue mit dem vorherigen verglichen 
                    //ob die ersten 2 teile vom namen gleich ist
                    $a = explode("_", $pics[$j-1]);
                    $b = explode("_", $pics[$j]);
                    if(($a[0]==$b[0])&&($a[1]==$b[1])){
                        echo '<img style="width:150px;height:150px" src="data/news/pics/'
                        .$pics[$j].'" alt="'.file_get_contents("data/news/header/".$header[$i]).'">';
                        $j++;
                    } else {
                        break;
                    }
                }
                echo "<p>".file_get_contents("data/news/text/".$text[$i])."</p>";
                echo '</div>';
            }
            echo "</div>";
            
            if($_SESSION['loggedIn']&&$_SESSION['asAdmin']){
                echo '<form action="index.php" method="post" enctype="multipart/form-data">';
                    echo '<div class="col-12 col-sm-6">';
                        echo '<label>title:</label>';
                        echo '<input type="text" class="form-control" placeholder="title" name="title">';
                    echo '</div>';
                    echo '<div class="col-12">';
                        echo '<label>text:</label>';
                        echo '<textarea type="text" class="form-control" placeholder="text" name="text"></textarea>';
                    echo '</div>';
                    echo '<div class="col-12">';
                        echo '<label>Select image to upload:</label>';
                        echo '<input type="file" name="fileToUpload" id="fileToUpload">';
                    echo '</div>';
                    echo '<div class="col-12 col-sm-6">';
                        echo '<label>title to delete:</label>';
                        echo '<input type="text" class="form-control" placeholder="title to delete" name="titleToDel">';
                    echo '</div>';
                    echo '<div class="col-12 text-left">';
                        echo '<button type="submit" class="btn btn-danger btn-sm center">Submit</button>';
                    echo '</div>';
                echo '</form>';
            }
            //var_dump(count($header));
            //var_dump($header);
        ?>
    </div>
</section>