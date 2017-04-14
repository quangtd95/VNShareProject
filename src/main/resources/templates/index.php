<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trang chu</title>
</head>
<body>
<header style="height: 200px;background-color: red;">
    <h1 style="text-align: center"><B>XEM THONG TIN PHONG BAN</B></h1>
</header>
<table width="100%">
    <tr>
        <th style="width: 20%;height:400px;background-color: yellow">

        </th>
        <th style="width: 60%;height:400px">
            <table border="1px" style="margin: 0 auto" width="100%">
                <thead>
                <tr>
                    <th style="background-color: gray"><span>Mã số phòng ban</span></th>
                    <th style="background-color: gray"><span>Mô tả</span></th>
                    <th style="background-color: gray"><span>Thời gian</span></th>
                    <th style="background-color: gray"><span>Xem NV</span></th>
                </tr>
                </thead>
                <tbody>
                <?php
                $link =mysqli_connect("localhost:3306","root","","DULIEU");
                $result = mysqli_query($link,"select * from `Phongban`");
                while ($row = mysqli_fetch_row($result)){
                    echo "<tr><th>".$row{0}."</th><th>".$row{1}."</th><th>".$row{2}."</th><th><a href="."XemThongTinNV.php?IDPB=".$row{0}.">...</a></th></tr>";
                }
                ?>
                </tbody>


            </table>

        </th>
        <th style="width: 20%;height:400px;background-color: orange">

        </th>
    </tr>
</table>
<footer  style="height: 200px;background-color: black;"></footer>
</body>
</html>