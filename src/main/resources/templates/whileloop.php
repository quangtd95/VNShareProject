<?php

$i = 0;
$t = 1;
while(true) {
    $i += 2;
    if ($i > 10) break;
    $t *= $i;
    echo $t;
    echo "<br/>";
}