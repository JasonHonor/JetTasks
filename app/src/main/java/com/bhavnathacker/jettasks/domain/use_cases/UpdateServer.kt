package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.repository.UserPreferenceRepository

class UpdateServer(val repository: UserPreferenceRepository) {
        suspend operator fun invoke(server: String) {
            repository.updateServer(server)
        }
    }