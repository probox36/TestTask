
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

