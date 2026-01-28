/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function scrollToTable() {
    var table = document.getElementById('form:studentAcademicModelDT');
    if (table) {
        table.scrollIntoView({ behavior: 'smooth', block: 'start' });
        table.focus();
    }
}
