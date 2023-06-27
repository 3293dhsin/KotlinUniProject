<h1>Local Database - Room</h1>

<h3>Why using Room instead of SQLite</h3>
<p>Room persistence library provides abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.</p>
<ul>
  <li>Complile-time verification of SQL queries.</li>
  <li>Convenience annotations that minimize repetitive and error-prone boilerplate code.</li>
  <li>Streamlined database migration paths.</li>
</ul>
<h3>Setup</h3>
<p>Include the following dependencies to your app's <i>build.gradle</i> file.</p>
<code>val room_version = "2.5.2"
 implementation("androidx.room:room-runtime:$room_version")
 annotationProcessor("androidx.room:room-compiler:$room_version")</code>

<h3>Primary Components</h3>
<p>There are three major components in Room:</p>
<ul>
  <li><code>Database class</code> holds the database and serves as the main access point for the underlying connection to the app's persisted data.</li>
  <li><code>Data Entities</code> represent tables in the app's database.</li>
  <li><code>Data Access Objects(DAOs)</code> provide methods that the app can use to query, update, insert, and delete data in the database.</li>
</ul>
<img src="https://developer.android.com/static/images/training/data-storage/room_architecture.png" width="500" height="400">
