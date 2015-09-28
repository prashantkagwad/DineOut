prefix user:<http://www.utdallas.edu/~pdk130030/owl/user.owl#>

SELECT ?Name ?Pref ?Lat ?Long 
WHERE {
  user:User_Home user:Name ?Name.
  user:User_Home user:Pref ?Pref.
  user:User_Home user:Is_At $a.
  $a user:Latitude ?Lat.
  $a user:Longitude ?Long.
}