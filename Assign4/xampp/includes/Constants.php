<?php 

    $servername = "localhost";
    $username = "root";
    $password = "";
    $database = "android";

    $conn = new mysqli($servername, $username, $password, $database);

    if ($conn->connect_error) {
        die("Connection error: " . $conn->connect_error);
    }

    $items = array();

    $results = "SELECT * FROM photo;";

    // prepare an SQl statement
    $stmt = $conn->prepare($results);

    $stmt->execute();

    $stmt->bind_result($id, $image, $imagethumb);

    while ($stmt->fetch()) {
        $temp = [
            'id'=>$id,
            'image'=>$image,
            'imagethumb'=>$imagethumb
        ];
        // push temp into items array
        array_push($items, $temp);
    }
    echo json_encode($items);
?>