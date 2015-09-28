prefix rest:<http://www.utdallas.edu/~pdk130030/owl/resturant.owl#>

SELECT ?Name ?Contents ?Type ?Price ?Category ?Served_At
WHERE {
  $a rest:Item_Name ?Name.
  $a rest:Contents ?Contents.
  $a rest:Item_Type ?Type.
  $a rest:Item_Price ?Price.
  $a rest:Item_Category ?Category.
  $a rest:Is_Served_At $b.
  $b rest:Resturant_Name ?Served_At.
}