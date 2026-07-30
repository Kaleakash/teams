// =========================================
// SmartMart Trade Dashboard
// script.js
// =========================================


// Sample Trade Data
// Later we can move this to trades.json

const trades = [

    {id:1001,symbol:"TCS",quantity:25,price:3500,status:"BUY"},

    {id:1002,symbol:"INFY",quantity:15,price:1550,status:"SELL"},

    {id:1003,symbol:"WIPRO",quantity:50,price:280,status:"BUY"},

    {id:1004,symbol:"HCL",quantity:35,price:1200,status:"PENDING"},

    {id:1005,symbol:"RELIANCE",quantity:12,price:2600,status:"BUY"},

    {id:1006,symbol:"SBI",quantity:80,price:790,status:"SELL"},

    {id:1007,symbol:"ITC",quantity:120,price:470,status:"BUY"},

    {id:1008,symbol:"LT",quantity:10,price:3700,status:"BUY"},

    {id:1009,symbol:"ICICI",quantity:42,price:1150,status:"SELL"},

    {id:1010,symbol:"HDFC",quantity:28,price:1720,status:"BUY"}

];


// =========================================
// Global Variables
// =========================================

let filteredTrades=[...trades];

let currentPage=1;

const rowsPerPage=5;

let sortColumn="id";

let ascending=true;


// =========================================
// HTML Elements
// =========================================

const tableBody=document.getElementById("tableBody");

const searchBox=document.getElementById("searchBox");

const loading=document.getElementById("loading");

const noData=document.getElementById("noData");

const pageInfo=document.getElementById("pageInfo");


// =========================================
// Initial Load
// =========================================

renderTable();


// =========================================
// Render Table
// =========================================

function renderTable(){

    tableBody.innerHTML="";

    loading.style.display="block";

    noData.style.display="none";

    setTimeout(()=>{

        loading.style.display="none";

        const start=(currentPage-1)*rowsPerPage;

        const end=start+rowsPerPage;

        const pageData=filteredTrades.slice(start,end);

        if(pageData.length===0){

            noData.style.display="block";

            return;

        }

        pageData.forEach(trade=>{

            tableBody.innerHTML+=`

            <tr>

                <td>${trade.id}</td>

                <td>${trade.symbol}</td>

                <td>${trade.quantity}</td>

                <td>₹${trade.price}</td>

                <td>

                    <span class="status ${trade.status.toLowerCase()}">

                        ${trade.status}

                    </span>

                </td>

                <td class="actions">

                    <button class="view"

                        onclick="viewTrade(${trade.id})">

                        View

                    </button>

                    <button class="edit"

                        onclick="editTrade(${trade.id})">

                        Edit

                    </button>

                    <button class="delete"

                        onclick="deleteTrade(${trade.id})">

                        Delete

                    </button>

                </td>

            </tr>

            `;

        });

        pageInfo.innerHTML=
            `Page ${currentPage}`;

    },500);

}



// =========================================
// Search
// =========================================

searchBox.addEventListener("keyup",()=>{

    const keyword=searchBox.value.toLowerCase();

    filteredTrades=trades.filter(trade=>

        trade.symbol.toLowerCase().includes(keyword)

    );

    currentPage=1;

    renderTable();

});



// =========================================
// Sorting
// =========================================

document.querySelectorAll("th[data-column]")

.forEach(header=>{

    header.addEventListener("click",()=>{

        const column=header.dataset.column;

        if(sortColumn===column){

            ascending=!ascending;

        }

        else{

            sortColumn=column;

            ascending=true;

        }

        filteredTrades.sort((a,b)=>{

            if(a[column]>b[column])

                return ascending?1:-1;

            if(a[column]<b[column])

                return ascending?-1:1;

            return 0;

        });

        renderTable();

    });

});



// =========================================
// Pagination
// =========================================

document

.getElementById("previousBtn")

.addEventListener("click",()=>{

    if(currentPage>1){

        currentPage--;

        renderTable();

    }

});


document

.getElementById("nextBtn")

.addEventListener("click",()=>{

    const totalPages=Math.ceil(

        filteredTrades.length/rowsPerPage

    );

    if(currentPage<totalPages){

        currentPage++;

        renderTable();

    }

});



// =========================================
// Refresh
// =========================================

document

.getElementById("refreshBtn")

.addEventListener("click",()=>{

    searchBox.value="";

    filteredTrades=[...trades];

    currentPage=1;

    renderTable();

});



// =========================================
// Action Buttons
// =========================================

function viewTrade(id){

    alert("Viewing Trade : "+id);

}


function editTrade(id){

    alert("Editing Trade : "+id);

}


function deleteTrade(id){

    alert("Deleting Trade : "+id);

}