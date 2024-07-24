import { addUser, getAllUsers, deleteUser } from "./Utils/Fetcher";

updateTable()

export async function addBtnOnClick() {

    let name = document.querySelector(".NameField").value;
    let lastName = document.querySelector(".LastNameField").value;
    let patronymic = document.querySelector(".PatronymicField").value;
    let dob = document.querySelector(".DOBField").value;
    let group = document.querySelector(".GroupField").value;
    
    if (name != '' && lastName != '' && patronymic != '' && dob != '' && group != '') {
        await addUser({ name, lastName, patronymic, dob, group }).then(() => { 
            updateTable()
            document.querySelectorAll("input:not(.IdField)").forEach((el) => {
                el.value = ''
            }) 
        });
    } else {
        alert('Заполните все поля кроме id')
    }
}

export async function deleteBtnOnClick() {

    let idField = document.querySelector(".IdField") 
    let id = idField.value;
    
    if (id != '') {
        await deleteUser(id).then(
            () => { updateTable(); idField.value = '' }
        )
    } else {
        alert('Заполните поле id')
    }
}

export async function updateTable() {
    let users = await getAllUsers();
    let table = document.querySelector("table")
    document.querySelectorAll("tr:not(.TableHeader)").forEach((el) => { el.remove() })
    
    users.forEach(user => {
        const newRow = table.insertRow();

        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);
        const cell4 = newRow.insertCell(3);
        const cell5 = newRow.insertCell(4);
        const cell6 = newRow.insertCell(5);

        cell1.textContent = user.id;
        cell2.textContent = user.name;
        cell3.textContent = user.lastName;
        cell4.textContent = user.patronymic;
        cell5.textContent = user.dob;
        cell6.textContent = user.group;
    });
}
