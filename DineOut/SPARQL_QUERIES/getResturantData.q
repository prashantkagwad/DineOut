prefix rest:<http://www.utdallas.edu/~pdk130030/owl/resturant.owl#>

SELECT ?Name ?Cuisine ?Ambience ?Price ?Lat ?Long
WHERE {
  $a rest:Resturant_Name ?Name.
  $a rest:Cuisine ?Cuisine.
  $a rest:Ambience ?Ambience.
  $a rest:Resturant_Price ?Price.
  $a rest:Is_At $b.
  $b rest:Latitude ?Lat.
  $b rest:Longitude ?Long.
}