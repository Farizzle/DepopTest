package com.example.depoptest.data.remote.response.model

import java.io.Serializable

data class Formats(
    val P1: Picture, /** 640 x 640 */
    val P2: Picture, /** 150 x 150 */
    val P4: Picture, /** 210 x 210 */
    val P5: Picture, /** 320 x 320 */
    val P6: Picture, /** 480 x 480 */
    val P7: Picture, /** 960 x 960 */
    val P8: Picture  /** 1280 x 1280 */
) : Serializable