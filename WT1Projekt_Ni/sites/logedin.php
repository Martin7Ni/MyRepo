<div>
    <?php
        for($i=0;$i<count($meinXML->user);$i++){
            if(($_POST['username']==$meinXML->user[$i]->username)){
                echo '<p>Logged in as '.$meinXML->user[$i]->firstname.
                        ' '.$meinXML->user[$i]->lastname.'.</p>';
            }
        }
    ?>
</div>
<div>
    <form action="index.php" method="post">
        <input type="submit" name="logoff" value="Logoff">
    </form>
</div>
<div>
    <?php
        if(!$_SESSION['asAdmin']){
            echo '<p>'.count($_SESSION['warenkorb']).'X</p>';
            echo '<a href="index.php?menu=warenkorb"><img src="pics/basket.png" style="width:32px;height:32px"></a>';
            $sum=0;
            for($i=0;$i<count($_SESSION['warenkorb']);$i++){
                $sum = $sum + $jsonArray[$_SESSION['warenkorb'][$i]-1]['preis'];
            }
            echo '<p>'.$sum.'</p>';
        }
    ?>
</div>