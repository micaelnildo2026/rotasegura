package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.MobilityRepository
import com.example.model.MobilityContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("RotaSegura", appName)
  }

  @Test
  fun `verify mobility repository provides calculated routes and safety score`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = AppDatabase.getDatabase(context)
    val repository = MobilityRepository(db.incidentDao())

    val routes = repository.getCalculatedRoutes(MobilityContext())
    assertTrue("Should contain routes", routes.isNotEmpty())

    val recommendedRoute = routes.first()
    assertTrue("Recommended route should have high safety score", recommendedRoute.safetyScore >= 90)
    assertTrue("Recommended route should be marked recommended", recommendedRoute.isRecommended)
  }

  @Test
  fun `verify b2g insights populated`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = AppDatabase.getDatabase(context)
    val repository = MobilityRepository(db.incidentDao())

    val insights = repository.getB2GInsights()
    assertTrue("B2G insights should not be empty", insights.isNotEmpty())
    assertNotNull(insights.first().recommendedAction)
  }
}

