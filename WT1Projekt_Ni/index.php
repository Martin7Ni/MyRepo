<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<?php
    $jsonString = file_get_contents("FHTW_Merchandise/produkte.json");
    $jsonArray = json_decode($jsonString, true);
    $meinXML = simplexml_load_file("user.xml");
    session_start();
    if(!isset($_SESSION['loggedIn']))$_SESSION['loggedIn'] = false;
    if(!isset($_SESSION['asAdmin']))$_SESSION['asAdmin'] = false;
    
    if((isset($_COOKIE['username']))&&(isset($_COOKIE['password']))){
        $_POST['username'] = $_COOKIE['username'];
        $_POST['password'] = $_COOKIE['password'];
    }
    
    if(isset($_POST['logoff'])){
        $_SESSION['loggedIn'] = false;
        $_POST['username'] = '';
        $_POST['password'] = '';
        setcookie('username', '', time() - 9898989);
        setcookie('password', '', time() - 9898989);
    }else
    if((isset($_POST['username'])) && (isset($_POST['password']))){
        //läuft das array user durch
        for($i=0;$i<count($meinXML->user);$i++){
            //vergleicht ob username und pw von post gleich dem von der xml datei ist
            if(($_POST['username']==$meinXML->user[$i]->username)
                    &&($_POST['password']==$meinXML->user[$i]->password)){
                //falls ja wird loggedIn wahr und dann wird überprüft ob admin oder nicht
                $_SESSION['loggedIn'] = true;
                if($meinXML->user[$i]['type']=='admin'){
                    $_SESSION['asAdmin'] = true;
                } else {
                    $_SESSION['asAdmin'] = false;
                }
                //wenn 'keep me logged in' angeklicht wurde wird cookie auf 1 tag gesetzt
                if(isset($_POST['box'])){
                    setcookie('username', $_POST['username'], time() + 60*60*24);
                    setcookie('password', $_POST['password'], time() + 60*60*24);
                } else {
                //sonst auf 1 stunde
                    setcookie('username', $_POST['username'], time() + 60*60);
                    setcookie('password', $_POST['password'], time() + 60*60);
                }
            }
        }
    }
    
    
    if(!isset($_SESSION['warenkorb']))$_SESSION['warenkorb'] = array();
    //im array warenkorb wird die ID von der ware gespeichert auf die '+' geklickt wurde
    if(!empty($_GET['plus'])){
        array_push($_SESSION['warenkorb'], (int)$_GET['plus']);
    }
    if(!empty($_GET['minus'])){
        //wurde '-' geklickt wird im array warenkorb nach dieser ID gesucht und diese einmal rausgelöscht aus dem array
        $i = array_search($_GET['minus'], $_SESSION['warenkorb']);
        if(is_numeric($i)){
            array_splice($_SESSION['warenkorb'], $i, 1);
        }
    }
?>
<html>
    <head>
        <title>FHTW Shop&News</title>
        <meta charset="UTF-8">
        <meta name="application-name" content="FHTW-Shop">
        <meta name="author" content="Jia Liang Martin Ni">
        <meta name="description" content="A shop to buy FHTW merchandises">
        <meta name="keywords" content="FH, FHTW, shop, merchandise, cap, bag, bagpack, t-shirt, pen, news">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
              rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                
                <button class="navbar-toggler" type="button" data-toggle="collapse" 
                        data-target="#navbarNav" aria-controls="#navbarNav" 
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="index.php?menu=news">News</a></li>
                        <?php
                        if($_SESSION['loggedIn'])
                        echo '<li class="nav-item"><a class="nav-link" href="index.php?menu=shop">Shop</a></li>';
                        ?>
                        <li class="nav-item"><a class="nav-link" href="index.php?menu=about">About</a></li>
                    </ul>
                </div>
            </div>
            <?php
                if($_SESSION['loggedIn']) include 'sites/logedIn.php';
                else include 'sites/loginform.php';
            ?>
        </nav>
        <main>
            <?php
                if(isset($_GET['menu'])){
                    switch($_GET['menu']){
                        case "news":
                            include 'sites/news.php';
                            break;
                        case "shop":
                            include 'sites/shop.php';
                            break;
                        case "about":
                            include 'sites/about.php';
                            break;
                        case "warenkorb":
                            include 'sites/warenkorb.php';
                            break;
                        case "impressum":
                            include 'sites/impressum.php';
                            break;
                    }
                } else {
                    include 'sites/news.php';
                }
                
                
                
                
                //var_dump($meinXML->user[0]);
                //echo ($meinXML->user[0]['type']);
            ?>
            
            
            
        </main>
        <div id="footers">
            <?php 
                include 'sites/footer.php'; 
            ?>
        </div>
    </body>
</html>
