
export async function addUser(user) {

    await fetch('http://localhost:3001/addUser', {
        headers: {
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(user)
    })
    .then( () => { console.log("POSTed successfully"); })
    .catch(
        err => { console.log(`Failed to POST ${user}: ${err}`); }
    );
}

export async function deleteUser(id) {

    await fetch('http://localhost:3001/deleteUser' + '?' + new URLSearchParams(
        { userId: id }).toString(), 
        { method: 'DELETE', }
    )
    .then( () => { console.log("DELETEd successfully"); })
    .catch(
        err => { console.log(`Failed to DELETE: ${err}`); }
    );
}

export async function getAllUsers() {

    let promise = await fetch('http://localhost:3001/getAllUsers', 
        { method: 'GET', }
    )
    .catch(
        err => { console.log(`Failed to GET: ${err}`); }
    );


    if (promise != undefined && promise.ok) {
        let users = await promise.json()
        return users
    }
}
