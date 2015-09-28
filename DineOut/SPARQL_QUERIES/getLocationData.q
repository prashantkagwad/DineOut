prefix loc:<http://www.utdallas.edu/~pdk130030/owl/location.owl#>

SELECT ?ID ?Name ?hScore ?Adj
WHERE {
  $a loc:Location_Name ?Name.
  $a loc:hScore ?hScore.
  $a loc:Adj_Locations ?Adj.
  $a loc:Location_ID ?ID.
}
ORDER BY ?ID