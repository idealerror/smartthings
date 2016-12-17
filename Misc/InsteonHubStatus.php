<?php
// Pull ?url= from URL
$url = $_GET['url'];

// Check if URL contains /sx.xml
if(!stristr($url,"sx.xml")){ die("Error: Cannot create object"); }

// Make call out to hub
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$hub_output = curl_exec ($ch);
curl_close ($ch);

// Parse XML string
$xml = simplexml_load_string($hub_output) or die("Error: Cannot create object");

// Convert object array into PHP array
$array = (array)$xml;

// Pull <X D="" />
$x = (array)$array['X'];

// Pull D=""
$status = $x['@attributes']['D'];

// Pull the last 2 characters of the D="" Output
$val = substr($status, -2);

// Pull the first 6 characters of the D="" Output
$device = substr($status, 0, 6);

// Create JSON Array
// Get the decimal value of 2 character HEX value
$json['num'] = hexdec($val);

// Convert decimal value to percentage, max num is 255 for 100%
$json['percent'] = round($json['num']/255*100);
$json['deviceid'] = $device;
if($json['percent'] > 0)
{
    $json['status'] = "on";
}
else
{
    $json['status'] = "off";
}

echo json_encode($json);