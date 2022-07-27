package com.juniordamacena.marpics.models.network

import com.juniordamacena.marpics.models.main.Rover

data class RoversApiResponse(
    val rovers: List<Rover>
)