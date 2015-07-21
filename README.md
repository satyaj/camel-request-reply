This project consists of 5 different approaches to solving the asynchronous request/reply pattern. The front end is a CXF Rest service, on the back end is an asynchronous server (simulated with a file endpoint). 

1. camel-requestreply-with-jms -> Uses JMS for implementing the request/reply pattern in a camel queue. Uses a hazelcast cache in the route.

2. camel-requestreply-without-jms -> Implements request/reply using seda queue.  Uses a hazelcast cache in the route.

3. camel-requestreply-without-jms-hazelcast -> Implements request/reply using seda queue. Removed the hazelcast cache in the route.
(This is one of the solutions being tested by the partner).

4. camel-requestreply-without-jms-hazelcast-seda -> Implements request/reply using direct endpoint and Asynchronous Processor.
(This is another solution being tested by the partner. I added this based on discussion with Charles about replacing seda with direct.

5. camel-requestreply-without-jms-with-ehcache -> Implements request/reply using ehcache and Asynchronous Processor.
(I developed this today. As we do not create new endpoints, this should be a faster approach to use. Please review it, I intend to propose this to the partner).


