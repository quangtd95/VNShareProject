<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>XEM THONG TIN NHAN VIEN</title>
</head>
<body>
<header style="height: 200px;background-color: red;">
    <h1 style="text-align: center"><B>XEM THONG TIN NHAN VIEN</B></h1>
</header>
<table width="100%">
    <tr>
        <th style="width: 20%;height:400px;background-color: yellow">
        </th>
        <th style="width: 60%;height:400px">
            <table border="1px" style="margin: 0 auto" width="100%">
                <thead>
                <tr>
                    <th style="background-color: gray"><span>Mã số nhan vien</span></th>
                    <th style="background-color: gray"><span>Họ tên</span></th>
                    <th style="background-color: gray"><span>Địa chỉ</span></th>
                    <th style="background-color: gray"><span>IDPB</span></th>
                </tr>
                </thead>
                <tbody>
                <?php
                $link =mysqli_connect("localhost:3306","root","","DULIEU");
                if (isset($_REQUEST['IDPB'])){
                    $IDPB = $_REQUEST['IDPB'];
                    if ($IDPB == null){
                        $result = mysqli_query($link,"select * from `Nhanvien`");
                    } else {
                        $result = mysqli_query($link,"select * from `Nhanvien` where `IDPB`='".$IDPB."'");
                    }

                    while ($row = mysqli_fetch_row($result)){
                        echo "<tr><th>".$row{0}."</th><th>".$row{1}."</th><th>".$row{2}."</th><th>".$row{3}."</th></tr>";
                    }
                }
                else {
                    echo "Nhap Ma So Phong Ban";
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