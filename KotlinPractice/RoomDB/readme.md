<h1>Local Database - Room</h1>

<h2>Why using Room instead of SQLite</h2>
<p>Room persistence library provides abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.</p>
<ul>
  <li>Complile-time verification of SQL queries.</li>
  <li>Convenience annotations that minimize repetitive and error-prone boilerplate code.</li>
  <li>Streamlined database migration paths.</li>
</ul>

<h2>Setup</h2>
<p>Include the following dependencies to your app's <i>build.gradle</i> file.</p>
<pre>
  <code>
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
  </code>
</pre>

<h2>Primary Components</h2>
<p>There are three major components in Room:</p>
<ul>
  <li><code>Database class</code> holds the database and serves as the main access point for the underlying connection to the app's persisted data.</li>
  <li><code>Data Entities</code> represent tables in the app's database.</li>
  <li><code>Data Access Objects(DAOs)</code> provide methods that the app can use to query, update, insert, and delete data in the database.</li>
</ul>
<img src="https://developer.android.com/static/images/training/data-storage/room_architecture.png" width="500" height="400">

<h2>Sample Implementation</h2>
<h3>Data Entity</h3>
<p>The following code defines a User data entity which represents a User table with attributes uid, first_name, and last_name.</p>
<pre>
  <code>
    @Entity
    data class User(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
    )
  </code>
</pre>
<h3>Data Access Object(DAOs)</h3>
<p>The following code defines a DAO called UserDao. It allows us to use methods to interact with data in the user table. </p>
<pre>
  <code>
    @Dao
    interface UserDao {
      @Query("SELECT * FROM user")
      fun getAll(): List<User> <br>
      @Query("SELECT * FROM user WHERE uid IN (:userIds)")
      fun loadAllByIds(userIds: IntArray): List<User>
      @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
      fun findNyName(first: String, last:String): User
      @Insert
      fun insertAll(vararg users: User)
      @Delete 
      fun delete(user: User)
    }
    
  </code>
</pre>
