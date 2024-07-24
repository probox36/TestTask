import { addUser } from "./Utils/Fetcher";

export function showMessage() {

    let name = document.querySelector(".NameField").textContent;
    let lastName = document.querySelector(".LastNameField").textContent;
    let patronymic = document.querySelector(".PatronymicField").textContent;
    let group = document.querySelector(".GroupField").textContent;

    alert(name)
    console.log({ name, lastName, patronymic, group });
    addUser({ name, lastName, patronymic, group });
}

export function echo() {
    alert('echo')
}

// document.querySelector(".addBtn").addEventListener("click", showMessage)

