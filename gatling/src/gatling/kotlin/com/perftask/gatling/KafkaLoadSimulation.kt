package com.perftask.gatling

import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import org.galaxio.gatling.kafka.javaapi.KafkaDsl.kafka
import com.perftask.gatling.util.FullNameGenerator
import com.perftask.gatling.util.InnGenerator
import java.util.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class KafkaLoadSimulation : Simulation() {

    private val feeder = generateSequence {
        mapOf(
            "msg_id" to UUID.randomUUID().toString(),
            "full_name" to FullNameGenerator.generate(),
            "inn" to InnGenerator.generate()
        )
    }.iterator()

    val kafkaProtocol = kafka().properties(mapOf("bootstrap.servers" to "kafka:9092"))

    val scn = scenario("Kafka Producer").feed(feeder).exec(
            kafka("send message").topic("message-in").send(
                    "#{msg_id}", """{"msg_id":"#{msg_id}","full_name":"#{full_name}","inn":"#{inn}"}"""
                )

        )

    init {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(10.0).during(5.minutes.toJavaDuration()),
                constantUsersPerSec(15.0).during(5.minutes.toJavaDuration()),
                constantUsersPerSec(25.0).during(5.minutes.toJavaDuration()),
                constantUsersPerSec(50.0).during(5.minutes.toJavaDuration()),
            )
        ).protocols(kafkaProtocol)
    }
}
