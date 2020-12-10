package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.data.source.local.LocalDataSource
import co.icanteach.apps.android.droidfeeds.data.source.remote.RemoteDataSource

class DroidFeedsRepository constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
)