<h2> Common library for services </h2>
- Connection with Elasticsearch
- Acces to the application.conf
<h2>Example</h2>
 ´´´scala
    class example extends Configuration {
        val text = conf.getString("text")
    }
 ´´´