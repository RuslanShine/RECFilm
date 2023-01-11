package com.example.recfilm.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recfilm.R
import com.example.recfilm.databinding.FragmentHomeBinding
import com.example.recfilm.domain.Film
import com.example.recfilm.utils.AnimationHelper
import com.example.recfilm.view.rv_adapters.FilmListRecyclerAdapter
import com.example.recfilm.view.MainActivity
import com.example.recfilm.view.rv_adapters.TopSpacingItemDecoration
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    val filmsDataBase = listOf(
        Film(
            "Interstellar",
            R.drawable.interstellar,
            "Будущее Земли было пронизано бедствиями, голодом и засухой. Есть только один способ обеспечить выживание человечества: межзвездное путешествие. Недавно обнаруженная червоточина в дальних уголках нашей Солнечной системы позволяет команде астронавтов отправиться туда, где раньше не бывал ни один человек, на планете, которая может иметь подходящую среду для поддержания человеческой жизни",
            2.1f
        ),
        Film(
            "The batman",
            R.drawable.the_batman,
            "Бэтмен отправляется в преступный мир Готэм-сити, когда убийца-садист оставляет за собой след загадочных улик. По мере того, как улики начинают приближаться к дому, а масштаб планов преступника становится ясным, он должен наладить новые отношения, разоблачить преступника и восстановить справедливость. к злоупотреблению властью и коррупции, которые давно преследуют мегаполис.",
            3.7f
        ),
        Film(
            "The social",
            R.drawable.the_social,
            "Осенним вечером 2003 года выпускник Гарварда и гений компьютерного программирования Марк Цукерберг садится за свой компьютер и с энтузиазмом начинает работать над новой идеей. В ярости ведения блога и программирования то, что начинается в его комнате в общежитии, вскоре становится глобальной социальной сетью и революцией в общении. Всего через шесть лет и 500 миллионов друзей Марк Цукерберг стал самым молодым миллиардером в истории… но для этого предпринимателя успех приводит как к личным, так и к юридическим осложнениям.",
            9.7f
        ),
        Film(
            "Star vars",
            R.drawable.star_vars_poster,
            "Спустя 30 лет после поражения Дарта Вейдера и Империи Рей, мусорщик с планеты Джакку, находит дроида BB-8, который знает местонахождение давно потерянного Люка Скайуокера. Рей, а также штурмовик-изгой и два контрабандиста оказываются в эпицентре битвы между Сопротивлением и устрашающими легионами Первого Ордена.",
            7.7f
        ),
        Film(
            "Spider man",
            R.drawable.spider_man,
            "Тайная личность Питера Паркера раскрывается всему миру. Отчаянно нуждаясь в помощи, Питер обращается к Доктору Стрэнджу, чтобы заставить мир забыть, что он Человек-Паук. Заклинание идет ужасно неправильно и разрушает мультивселенную, вызывая чудовищных злодеев, которые могут уничтожить мир.",
            8.7f
        ),
        Film(
            "Ready player one",
            R.drawable.ready_player_one,
            "В 2045 году реальный мир стал суровым местом. Единственный раз, когда Уэйд Уоттс (Тай Шеридан) по-настоящему чувствует себя живым, это когда он сбегает в ОАЗИС, захватывающую виртуальную вселенную, где большинство людей проводит свои дни. В OASIS вы можете идти куда угодно, делать что угодно, быть кем угодно — единственными ограничениями являются ваше собственное воображение. ОАЗИС был создан блестящим и эксцентричным Джеймсом Холлидеем (Марк Райлэнс), который оставил свое огромное состояние и полный контроль над Оазисом победителю конкурса, состоящего из трех частей, который он задумал, чтобы найти достойного наследника. Когда Уэйд справится с первым испытанием в искажающей реальность охоте за сокровищами, он и его друзья — The High Five — попадут в фантастическую вселенную открытий и опасностей, чтобы спасти ОАЗИС.",
            6.6f
        ),
        Film(
            "Queens gambit",
            R.drawable.queens_gambit,
            "Девятилетняя сирота Бет Хармон тихая, угрюмая и внешне ничем не примечательная. То есть, пока она не сыграет свою первую партию в шахматы. Ее чувства обостряются, мышление становится яснее, и впервые в жизни она чувствует, что полностью контролирует ситуацию. К шестнадцати годам она участвует в Открытом чемпионате США. Но по мере того, как Бет оттачивает свои навыки в профессиональной сфере, ставки становятся выше, ее изоляция становится все более пугающей, а мысль о побеге становится все более заманчивой. По книге Уолтера Тевиса.",
            5.2f
        ),
        Film(
            "Inception",
            R.drawable.inception,
            "Дом Кобб — искусный вор, лучший в опасном искусстве добычи, он крадет ценные секреты из глубины подсознания во сне, когда разум наиболее уязвим. Редкие способности Кобба сделали его желанным игроком в этом предательском новом мире корпоративного шпионажа, но также сделали его международным беглецом и стоили ему всего, что он когда-либо любил. Теперь Коббу предлагают шанс на искупление. Одна последняя работа может вернуть ему жизнь, но только если он сможет совершить невозможное, начало. Вместо идеального ограбления Коббу и его команде специалистов приходится проворачивать обратное: их задача — не украсть идею, а внедрить ее. Если им это удастся, это может быть идеальное преступление. Но никакое тщательное планирование или опыт не могут подготовить команду к встрече с опасным врагом, который, кажется, предугадывает каждое их движение. Враг, которого мог предвидеть только Кобб.",
            4.7f
        ),
        Film(
            "Anna inventing",
            R.drawable.anna_inventing,
            "Дом Кобб — искусный вор, лучший в опасном искусстве добычи, он крадет ценные секреты из глубины подсознания во сне, когда разум наиболее уязвим. Редкие способности Кобба сделали его желанным игроком в этом предательском новом мире корпоративного шпионажа, но также сделали его международным беглецом и стоили ему всего, что он когда-либо любил. Теперь Коббу предлагают шанс на искупление. Одна последняя работа может вернуть ему жизнь, но только если он сможет совершить невозможное, начало. Вместо идеального ограбления Коббу и его команде специалистов приходится проворачивать обратное: их задача — не украсть идею, а внедрить ее. Если им это удастся, это может быть идеальное преступление. Но никакое тщательное планирование или опыт не могут подготовить команду к встрече с опасным врагом, который, кажется, предугадывает каждое их движение. Враг, которого мог предвидеть только Кобб.",
            9.9f
        ),
        Film(
            "The Shawshank Redemption",
            R.drawable.shawshank,
            "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            1.7f
        )
    )

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeFragmentRoot,
            requireActivity(),
            1
        )

        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)

        //нажатие на поле "поиск" целиком
            binding.searchView.setOnClickListener {
                binding.searchView.isIconified = false
        }

        //Слушатель изменений введенного текста
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Обработка нажатия "поиск" на клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Обработка кайдого изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст вставляем всю БД в адаптер
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на подходящие сочетания
                val result = filmsDataBase.filter {
                    //Приводим запрос и ммя фильма к нижнему регистру
                    it.title.toLowerCase(Locale.getDefault())
                        .contains(newText.toLowerCase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }
}