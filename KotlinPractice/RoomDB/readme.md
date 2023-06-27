<h2>Local Database - Room</h2>

<h3>Why using Room instead of SQLite</h3>
<p>Room persistence library provides abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.</p>
<ul>
  <li>Complile-time verification of SQL queries.</li>
  <li>Convenience annotations that minimize repetitive and error-prone boilerplate code.</li>
  <li>Streamlined database migration paths.</li>
</ul>
<h3>Setup</h3>
<p>Include the following dependencies to your app's build.gradle file.</p>
<code>val room_version = "2.5.2"
implementation("androidx.room:room-runtime:$room_version")
annotationProcessor("androidx.room:room-compiler:$room_version")</code>
