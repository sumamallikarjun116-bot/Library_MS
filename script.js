function filterTable(inputId,tableId){
 const q=document.getElementById(inputId).value.toLowerCase();
 const rows=document.getElementById(tableId).getElementsByTagName("tr");
 for(let i=1;i<rows.length;i++){
   rows[i].style.display=rows[i].innerText.toLowerCase().includes(q)?"":"none";
 }
}

document.addEventListener("DOMContentLoaded",()=>{
 const due=document.querySelector('input[name="dueDate"]');
 if(due){
   const d=new Date(); d.setDate(d.getDate()+14);
   due.value=d.toISOString().split("T")[0];
 }
});
