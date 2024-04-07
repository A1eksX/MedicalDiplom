export async function HttpNoData(url, method, jwt) {

    try {
        var response = await fetch(
            url, {
            method: method, // *GET, POST, PUT, DELETE, etc.
            headers: {
                "Content-Type": "application/json",
                "Authorization": 'Bearer ' + jwt
            }
        });

        if (response.ok) {

            try {
                var responsejson = await response.json();
                return { statusSuccessful: true, isDataEmpty: false, data: responsejson };
            } catch (error) {
                return { statusSuccessful: true, isDataEmpty: true, error: error };
            }
        }
        else return { statusSuccessful: false, error: response.status };
        
    } catch (error) {
        return { statusSuccessful: false, error: error };
    }
}

export async function HttpData(url, method, jwt, body) {
    
    try {
        var response = await fetch(
            url, {
            method: method, // *GET, POST, PUT, DELETE, etc.
            headers: {
                "Content-Type": "application/json",
                "Authorization": 'Bearer ' + jwt
            },
            body: JSON.stringify(body)
        });

        if (response.ok) {

            try {
                var responsejson = await response.json();
                return { statusSuccessful: true, isDataEmpty: false, data: responsejson };
            } catch (error) {
                return { statusSuccessful: true, isDataEmpty: true, error: error };
            }
        }
        else return { statusSuccessful: false, error: response.status };
        
    } catch (error) {
        return { statusSuccessful: false, error: error };
    }
}